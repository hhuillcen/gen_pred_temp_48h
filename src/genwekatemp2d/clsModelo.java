package genwekatemp2d;

import weka.classifiers.Classifier;
//import weka.classifiers.trees.REPTree;
import weka.classifiers.lazy.KStar;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class clsModelo {
    //private final Classifier Classifier = new REPTree();
    private final Classifier Classifier = new KStar();
    private final Instances train;
    
    public clsModelo(String rutaCorpus) throws Exception {
        train = ConverterUtils.DataSource.read(rutaCorpus);
        train.setClassIndex(6);
    }
    
    /* 
        Función que genera el modelo en base al corpus de entrenamiento arff.
        Recibe como parámetro la ruta donde deseamos generar el modelo.
    */
    public void generarModelo(String rutaDestino)throws Exception{
        if(train.numInstances()==0)
            throw new Exception("No classifier available");
        weka.core.SerializationHelper.write(rutaDestino, Classifier);
    }
}