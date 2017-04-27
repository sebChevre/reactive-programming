package ch.sebooom.reactive.rx.creations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Emitter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.SyncOnSubscribe;


/**
 * Exemples de codes basée sur la création d'Observable avec la méthode statique create
 * @author seb
 *
 */
public class Create {

	private final static Logger logger = LoggerFactory.getLogger(Create.class.getName());
	
	public static void main(String[] args) {
		create();
		//create_java7();
		//create_2();
	}

	public static Observable observableSyncOnSubscribe () {

		return Observable.create(new SyncOnSubscribe() {
			@Override
			public void call(Object o) {

			}

			@Override
			protected Object generateState() {
				return null;
			}

			@Override
			protected Object next(Object o, Observer observer) {
				return null;
			}
		});

	}

	/**
	 * Creation d'un observable from scratch
	 */
	public static void create(){


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
	
	/**
	 * Creation d'un observable from scratch java 7 style
	 */
	public static void create_j7(){
		logger.info("****** Observable standard create java 7 *****");

		Observable.create(new Action1<Emitter<Integer>>() {
			@Override
			public void call(Emitter<Integer> integerEmitter) {
				for(int cpt=0; cpt < 100; cpt++){
					integerEmitter.onNext(cpt);
				}

				//fin de flux
				integerEmitter.onCompleted();
			}
		}, Emitter.BackpressureMode.BUFFER)
				.subscribe(
						new Action1<Integer>() {
							@Override
							public void call(Integer entier) {
								logger.info("Valeur reçue: " + entier);
							}
						},
						new Action1<Throwable>() {
							@Override
							public void call(Throwable erreur) {
								logger.info("Valeur reçue: " + erreur);
							}
						},
						new Action0() {
							@Override
							public void call() {
								logger.info("Flux terminé");
							}
						});

	}


	/**
	 * Observable avec découpage et références
	 */
	public static void create_decompose () {

		logger.info("****** Observable standard create with filters *****");

		//Observable retournant une suite de nombre entier
		Observable<Integer> fluxEntier = Observable.create(emitter -> {
			try{
				
				for(int val = 0; val < 100; val++){
					emitter.onNext(val);
				}
				
				emitter.onCompleted();
				
			}catch (Exception e){
				emitter.onError(e);
			}
		}, Emitter.BackpressureMode.BUFFER);
	
		//Un subscriber est une implementation par defaut de Observer
		Subscriber<Integer> observer = new Subscriber<Integer>() {

			public void onNext(Integer item) {
				logger.info("Next: " + item);
			}

	        @Override
	        public void onError(Throwable error) {
				logger.info("Error: " + error.getMessage());
	        }
	
	        @Override
	        public void onCompleted() {
				logger.info("Sequence complete.");
	        }
	    };
	    
	    //souscription au flux de l'observable
        fluxEntier.subscribe(observer);
        
        logger.info("****** Observable standard create with filters second subscribe *****");
        //Idem, methode light j8
        fluxEntier.subscribe(
        		(item)->{logger.info("Item:" + item);},
        		(error)->{logger.info("Error:" + error.getMessage());},
        		()->{logger.info("Flux terminé");}
        );
        
        logger.info("****** Observable standard create with filters odd numbers*****");

		//Spécialisation de l'obersavble afin de ne retourner que des nombes pairs
        Observable<Integer> integerPairsObservable = fluxEntier.filter(item -> {
        	return item % 2 == 0;
        });
        
        
        integerPairsObservable.subscribe(
        		(item)->{logger.info("Item:" + item);},
        		(error)->{logger.info("Error:" + error.getMessage());},
        		()->{logger.info("Flux terminé");}
        );
        
	}


}
