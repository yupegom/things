package hello;

/*
 * Copyright (c) 2016-2018 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

public class SampleConsumer {

  private static final Logger log = LoggerFactory.getLogger(SampleConsumer.class.getName());

  private final ReceiverOptions<Integer, String> receiverOptions;
  private final SimpleDateFormat dateFormat;

  public SampleConsumer(String bootstrapServers) {

    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    receiverOptions = ReceiverOptions.create(props);
    dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
  }

  public Disposable consumeMessages(String topic, CountDownLatch latch) {

    ReceiverOptions<Integer, String> options =
        receiverOptions
            .subscription(Collections.singleton(topic))
            .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
            .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
    Flux<ReceiverRecord<Integer, String>> kafkaFlux =
        KafkaReceiver.create(options)
            .receive()
            .map(
                record -> {
                  System.out.printf("Message received %s", record.value());
                  record.receiverOffset().acknowledge();
                  latch.countDown();
                  return record;
                });
    return kafkaFlux.subscribe(
        // record -> {
        //  ReceiverOffset offset = record.receiverOffset();
        //  System.out.printf(
        //      "Received message: topic-partition=%s offset=%d timestamp=%s key=%d value=%s\n",
        //      offset.topicPartition(),
        //      offset.offset(),
        //      dateFormat.format(new Date(record.timestamp())),
        //      record.key(),
        //      record.value());
        //  offset.acknowledge();
        //  latch.countDown();
        // }
        );
  }
}
