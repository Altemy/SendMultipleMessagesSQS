import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.time.LocalDateTime;

public class SendMultipleMessagesSQS {

    public static void main(String[] args) throws Exception {
        // Create Client
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        // Define Queue Url
        final String queueUrl = "https://sqs.ap-northeast-1.amazonaws.com/157634048600/MyTestQueue-001.fifo";
        
        // Define the number of messages you send to SQS
        int sendMsgNum = 10;

        // Display processing start time
        System.out.println("===========================================");
        System.out.println("Start Sending at : " + LocalDateTime.now().toString());
        System.out.println("===========================================");

        // Send Messages
        for (int i=0; i<sendMsgNum; i++){
            // Difine the sequence number for DedupulicationId which is necessary for FIFO Queue
            String seqNum = String.valueOf(i);

            // Create Messages to send
            SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody("Test Message " + seqNum)
                .withMessageDeduplicationId("dedepId_" + seqNum)
                .withMessageGroupId("Test001");

            // Send Message
            sqs.sendMessage(sendMsgRequest);
        }

        // Display processing start time
        System.out.println("===========================================");
        System.out.println("Finish Sending at : " + LocalDateTime.now().toString());
        System.out.println("===========================================");
    }
}
