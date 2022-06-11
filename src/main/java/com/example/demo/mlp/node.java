package com.example.demo.mlp;
import java.util.ArrayList;
import java.util.List;

public class node {
    private List<Double> data;
    private List<Double> weight;
    private Double net;
    private Double error;
 
    public node() {
        this.data = new ArrayList<Double> ();
        this.weight = new ArrayList<Double> ();
        this.net = 0.0;
        this.error = 0.0;
    }

    public void setWeight(List<Double> weight) {
        this.weight = weight;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public List<Double> getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
            this.weight.add(weight);
    }

    public Double getNet() {
        return net;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public Double linearDer(){
        return 0.1;
    }
    
    public Double linear (){
         return this.net/10;
    }

    public Double logistical (){
        return 1/(1+Math.exp(-this.net));
    }
    
    public Double logisticalDer (){
        return this.logistical()*(1*this.logistical());
    }

    public Double tangent(){
        return Math.tanh(this.net);
        //return (1-Math.exp(-(2*this.net)))/(1+Math.exp(-(2*this.net)));
    }

    public Double tangentDer (){
        return 1-Math.pow(this.tangent(),2);
    }

    public void calculateNet(){
        Double[] sum;
        Double totalSum = 0.0;
        sum = new Double[this.data.size()];

        for(int i=0;i<this.data.size();i++){
            sum[i] = this.data.get(i)*this.weight.get(i);
        }
        for(int i=0;i<this.data.size();i++){
            totalSum += sum[i];
        }
        this.net = totalSum;
    }

    public void calculateError(int desired, Double output, int option){
        switch(option) {
            case 1:
                this.error = (desired - output) * linearDer();
                break;
            case 2:
                this.error = (desired - output) * logisticalDer();
                break;
            case 3:
                this.error = (desired - output) * tangentDer();
                break;
        }
    }
    
    public void attweight(Double learningRate){
        for(int i=0;i<this.weight.size();i++){
            this.weight.set(i, this.weight.get(i) + (learningRate * this.error * this.data.get(i)));
        }
    }
}
