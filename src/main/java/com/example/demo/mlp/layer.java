package com.example.demo.mlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class layer {
    private ArrayList<node> nodes;
    private Double learningRate;
    private Double mistakeValue;
    private List<Double> dataentries;
    private List<Double> dataoutput;
    private int qtdnode;
    private int option;
   
    public layer(Double learningRate, Double mistakeValue, int qtd, int option) {
        this.nodes = new ArrayList<node>();
        this.learningRate = learningRate;
        this.mistakeValue = mistakeValue;
        this.qtdnode = qtd;
        this.dataentries = new ArrayList<Double>();
        this.dataoutput = new ArrayList<Double>();
        this.option = option;
        initializeNodes();
    }   

    private void initializeNodes() {
        for(int i=0; i<this.qtdnode; i++) {
            this.nodes.add(new node());
        }
    }

    public ArrayList<node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<node> nodes) {
        this.nodes = nodes;
    }

    public Double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    public Double getMistakeValue() {
        return mistakeValue;
    }

    public void setMistakeValue(Double mistakeValue) {
        this.mistakeValue = mistakeValue;
    }

    public List<Double> getDataentries() {
        return dataentries;
    }

    public void setDataentries(List<Double> vals) {
        this.dataentries = vals;
    }

    public List<Double> getDataoutput() {
        return dataoutput;
    }

    public void setDataoutput(List<Double> dataoutput) {
        this.dataoutput = dataoutput;
    }

    public void setWeight(int entries) {
        Double aux;
        Random rand = new Random();
        for(node i : this.nodes) {
            for(int j=0; j<entries; j++) {
                aux = rand.nextDouble();
                i.setWeight(aux);
            }
        }
    }

    public void calculateError(Double error, List<Double> weight){
        for(int i =0; i < this.nodes.size(); i++) {
            switch(this.option) {
                case 1:

                    this.nodes.get(i).setError((error*weight.get(i))*this.nodes.get(i).linearDer());
                    break;
                case 2:
                    this.nodes.get(i).setError(error*weight.get(i)*this.nodes.get(i).logisticalDer());
                    break;
                case 3:
                    this.nodes.get(i).setError(error*weight.get(i)*this.nodes.get(i).tangentDer());
                    break;
            }
            this.nodes.get(i).attweight(this.learningRate);
        }
    }

    public void attweight(){
        for(node i : this.nodes) {
            i.attweight(this.learningRate);
        }
    }

    public void trainning(){
        this.dataoutput = new ArrayList<Double>();
        for(node i : this.nodes) {
            i.setData(dataentries);
            i.calculateNet();
            switch(option) {
                case 1:
                    this.dataoutput.add(i.linear());
                    break;
                case 2:
                    this.dataoutput.add(i.logistical());
                    break;
                case 3:
                    this.dataoutput.add(i.tangent());
                    break;
            }
        }
    }
}
