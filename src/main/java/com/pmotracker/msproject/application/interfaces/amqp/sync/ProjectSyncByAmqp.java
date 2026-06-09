/*
 * ©2020 Nisum Technologies, Inc. All Rights Reserved.
 */
package com.pmotracker.msproject.application.interfaces.amqp.sync;

/*
 *  Created by IntelliJ IDEA
 *  User: Malik Imran (msabir@nisum.com)
 *  Date: 3/20/2020
 */

//import com.bgn.publisher.producer.LibProducer;
import com.pmotracker.msproject.application.dto.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectSyncByAmqp {

    //private final LibProducer producer;

    @Value("${queue.update.projects}")
    private String updateDealsQueue;

    public void onUpdate(ProjectResponseDto dealDto) {
        //producer.sendToTopic(updateDealsQueue, dealDto);
    }
}
