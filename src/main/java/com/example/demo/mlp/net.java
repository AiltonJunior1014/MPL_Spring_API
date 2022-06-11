package com.example.demo.mlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class net {
    private List<List<Double>> data;
    private ArrayList<String> desired;
    private ArrayList<String> desiredaux;
    private int[] desiredIndex;
    private layer layers;
    private Double error;
    private Double predicted;
    private int entry;
    private int exit;
    private Double errormin;
    private int options;
    private node outputNode;
    private Double learningRate;
    private int[][] confusionMatrix;
    public boolean errorstatus;
    
    
    public net(int entry, int exit, Double errormin, int options, Double learningRate) {
        this.data = new ArrayList<List<Double>>();
        this.desired = new ArrayList<String>();
        this.desiredaux = new ArrayList<String>();
        this.error = 0.0;
        this.entry = entry;
        this.exit = exit;
        this.errormin = errormin;
        this.options = options;
        this.learningRate = learningRate;
        this.outputNode = new node();
        this.errorstatus = false;
        setWeight();
    }

    public ArrayList<String> getDesired() {
        return desired;
    }

    public void setDesired(ArrayList<String> desired) {
        this.desired = desired;
    }

    public boolean getError() {
        return errorstatus;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public int getExit() {
        return exit;
    }

    public void setExit(int exit) {
        this.exit = exit;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public void setWeight() {
        Double aux;
        Random rand = new Random();
        for(int j=0; j<this.entry; j++) {
            aux = rand.nextDouble();
            this.outputNode.setWeight(aux);
        }
    }

    public void readArq(String arqName) throws IOException {
        BufferedReader br = null;
        String line = "";
        String csvDivisor = ",";
        int aux=0;
        String arqBaseCSV = arqName;
        ArrayList<String> Data, desiredAux;


        desiredAux = new ArrayList<String>();
        br = new BufferedReader(new FileReader(arqBaseCSV));
        line = br.readLine();
        
        while (line != null) {
            String[] data = line.split(csvDivisor);
            Data = new ArrayList<String>();
            for (int j = 0; j < data.length-1; j++) {
                Data.add(data[j]);
            }
            desiredAux.add(data[data.length-1]);
            this.data.add(new ArrayList<Double>());
            for (int i = 0; i < Data.size();i++){
                
                this.data.get(aux).add(Double.parseDouble(Data.get(i)));
            }
            aux++;            
            line = br.readLine();            
        }        
        br.close();


        Collections.sort(desiredAux);

        List<String> set;
        
        set = desiredAux.stream().distinct().collect(Collectors.toList());
        
        this.desired.addAll(desiredAux);
        this.desiredaux.addAll(set);

        this.desiredIndex = new int [this.desiredaux.size()];

        for(int i = 0; i < this.desiredaux.size(); i++){
            this.desiredIndex[i] = i+1;
        }
    }


    public void trainning(){
        int pos=0;
        List<Double> vals;
        
        this.layers= (new layer(this.learningRate,error,this.data.get(0).size(),this.options));
        this.layers.setWeight(this.entry);

        for(int index=0; index<this.data.size(); index++){
            vals = this.data.get(index);
            
            this.layers.setDataentries(vals);
            this.layers.trainning();
            
            this.outputNode.setData(this.layers.getDataoutput());
            this.outputNode.calculateNet();

            switch(this.options) {
                case 1:
                    this.predicted = this.outputNode.linear();
                    break;
                case 2:
                    this.predicted = this.outputNode.logistical();
                    break;
                case 3:
                    this.predicted = this.outputNode.tangent();
                    break;
            }

            pos = this.desiredaux.indexOf(this.desired.get(index));

            
            this.error = 0.5*Math.pow((pos - this.predicted),2);

            if(this.error < this.errormin){
                index = this.data.size()+1;
                this.errorstatus = true;
                System.out.printf("Iteration: "+index+" Error: %.5f\n",this.error);
            }
            else{
                this.outputNode.calculateError(pos, this.predicted, this.options);
                this.outputNode.attweight(this.learningRate);

                this.layers.calculateError(this.outputNode.getError(), this.outputNode.getWeight());
                this.layers.attweight();
            }

            //System.out.printf("Predicted: %.5f Desired: %s\n",this.predicted,this.desired.get(index));

        }

    }

    public void initializeMatrix(){
        this.confusionMatrix = new int[this.desiredIndex.length][this.desiredIndex.length];
        for(int i=0; i<this.desiredIndex.length;i++){
            for(int j=0; j<this.desiredIndex.length;j++){
                this.confusionMatrix[i][j] = 0;
            }
        }
    }

    
    public void test(String arqName) throws IOException {
        List<Double> vals;
        
        
        this.data = new ArrayList<List<Double>>();
        this.desiredIndex = new int [this.desiredaux.size()];
        this.desired = new ArrayList<String>();
        this.desiredaux = new ArrayList<String>();
        
        readArq(arqName);
        initializeMatrix();

        for(int index=0; index<this.data.size(); index++){
            vals = this.data.get(index);
            
            this.layers.setDataentries(vals);
            this.layers.trainning();
            
            this.outputNode.setData(this.layers.getDataoutput());
            this.outputNode.calculateNet();

            switch(this.options) {
                case 1:
                    this.predicted = this.outputNode.linear();
                    System.out.println(this.desired.get(index)+""+this.predicted);
                    break;
                case 2:
                    this.predicted = this.outputNode.logistical();
                    System.out.println(this.desired.get(index)+""+this.predicted);
                    break;
                case 3:
                    this.predicted = this.outputNode.tangent();
                    System.out.println(this.desired.get(index)+""+this.predicted);
                    break;
            }
            
            this.confusionMatrix[this.desiredIndex[this.desiredaux.indexOf(this.desired.get(index))]-1][(int)Math.round(this.predicted)]++;
            

            // System.out.printf("Iteration: "+index+" Error: %.5f\n",this.error);
            // System.out.printf("Predicted: %.5f Desired: %s\n",this.predicted,this.desired.get(index));
        }
        for(int i=0;i<this.confusionMatrix[0].length;i++){
            for(int j=0;j<this.confusionMatrix.length;j++){
                System.out.print(this.confusionMatrix[i][j]+" ");
            }
            System.out.println();
        }
    }

}
