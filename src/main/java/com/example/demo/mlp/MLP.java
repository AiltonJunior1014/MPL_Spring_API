package com.example.demo.mlp;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MLP {
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String init() throws IOException{
        this.run();
        return "leo";
    }



    public void run() throws IOException {
        net n = new net(6, 5, 0.00001, 1, 0.2);

        n.readArq("C:\\IA\\base_treinamento.csv");
        for(int i=0; i<2000; i++){
            System.out.println("Epoca: " + i);
            n.trainning();
            if(n.getError()){
                i = 2001;
            }
        }
        System.out.println("CAbo\n\n\n");
        System.out.println("teste");
        n.test("C:\\IA\\base_teste.csv");
    }
}
