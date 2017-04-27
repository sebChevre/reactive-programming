package ch.sebooom.reactive.rx.async;

import rx.Emitter;
import rx.Observable;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class AsyncExamples {

    logger.info("****** Observable standard create *****");

    Observable.create((emitter)->{
        //emission de 100  valeurs
        for(int cpt=0; cpt < 100; cpt++){
            emitter.onNext(cpt);
        }

        //fin de flux
        emitter.onCompleted();

    }, Emitter.BackpressureMode.BUFFER)
            //souscription
            .subscribe(
            entier->{
        logger.info("Valeur reçue: " + entier);
    },
    erreur->{
        logger.error("Erreur survenue: " + erreur.getMessage());
    },
            ()->{
        logger.info("Flux terminé");
    });

    logger.info("****** End method *****");
}
