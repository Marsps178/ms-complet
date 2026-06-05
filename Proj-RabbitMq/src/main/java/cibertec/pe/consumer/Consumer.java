package cibertec.pe.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = {""})
    public void receive(@Payload String mensaje){
        makeslow();
        System.out.println("Recibiendo mensaje: " + mensaje);
    }

    public void makeslow(){
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();

        }
    }

}
