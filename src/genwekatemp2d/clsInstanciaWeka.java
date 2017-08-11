package genwekatemp2d;

import java.io.IOException;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class clsInstanciaWeka {
    public Instance crearInstancia(Float hora_minuto,Float temperatura, Float humedad, Float viento, Float lluvia, Float uv_actual, Instances train) throws IOException{
        /* El número 2, es el número de atributos que aparecen en el corpus (clima y temperatura). */
        Instance instance = new Instance(7);
        
        /* Se escribe sólo los atributos sin considerar las clases, en este caso, solo queda temperatura */
        Attribute atributo = train.attribute("hora_minuto");
        instance.setValue(atributo, hora_minuto);
        Attribute atributo1 = train.attribute("temperatura");
        instance.setValue(atributo1, temperatura);
        Attribute atributo2 = train.attribute("humedad");
        instance.setValue(atributo2, humedad);
        Attribute atributo3 = train.attribute("viento");
        instance.setValue(atributo3, viento);
        Attribute atributo4 = train.attribute("lluvia");
        instance.setValue(atributo4, lluvia);
        Attribute atributo5 = train.attribute("uv_actual");
        instance.setValue(atributo5, uv_actual);

        instance.setDataset(train);

        
        return instance;
    }
}
