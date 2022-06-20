package com.example.demo.mlp;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class MLP {
    String arqpathtrainning;
    String arqpathtest;

    @RequestMapping(value = "/trainningpath", method = RequestMethod.POST)
    public void setPathtrainning(String arqpath) {
        this.arqpathtrainning = arqpath;
    }

    @RequestMapping(value = "/testpath", method = RequestMethod.POST)
    public void setPathtest(String arqpath) {
        this.arqpathtrainning = arqpath;
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public int[][] init() throws IOException{
        return this.run();
    }



    public int[][] run() throws IOException {
        net n = new net(6, 5, 0.00000001, 1, 0.0000009);

        n.readArq("C:\\IA\\base_treinamento.csv");
        for(int i=0; i<20000; i++){
            System.out.println("Epoca: " + i+" | "+n.getError());
            n.trainning();
            if(n.getErrorStatus()){
                i = 2000001;
            }
        
        return n.test("C:\\IA\\base_teste.csv");
    }
}
