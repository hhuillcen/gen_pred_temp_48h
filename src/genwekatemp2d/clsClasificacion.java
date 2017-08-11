package genwekatemp2d;

import weka.classifiers.Classifier;
//import weka.classifiers.trees.REPTree;
import weka.classifiers.lazy.KStar;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class clsClasificacion {
    private static Instances train;
    Classifier Classifier;
    Instances data;
    
    public clsClasificacion(String corpus, String modelo) throws Exception{
        //Classifier = (REPTree) weka.core.SerializationHelper.read(modelo);
        Classifier = (KStar) weka.core.SerializationHelper.read(modelo);
        train = ConverterUtils.DataSource.read(corpus);
        train.setClassIndex(6); 
        data = new Instances(train);
    }
    
    public String clasificar(Float hora_minuto,Float temperatura, Float humedad, Float viento, Float lluvia, Float uv_actual) throws Exception{
        double predicted;
        Instance instance;
        clsInstanciaWeka instancia = new clsInstanciaWeka();

        if(train.numInstances()==0){
            throw new Exception("No classifier available");
        }

        instance = instancia.crearInstancia(hora_minuto, temperatura, humedad, viento, lluvia, uv_actual, data);
        Classifier.buildClassifier(data);
        predicted = Classifier.classifyInstance(instance);
        predicted=Math.abs((Math.rint(predicted*100))/100); 
        //System.out.println(""+predicted);
        //return train.classAttribute().value((int)predicted);
        return (""+predicted);
    }
}
