/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.carr0676.heatbeat.presentation;
import cst8218.carr0676.heartbeat.entity.HeartFacade;
import javax.inject.Inject;
import javax.ejb.Singleton;
import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import java.util.List;
import cst8218.carr0676.heartbeat.entity.Heart;

/**
 *
 * @author Dylan
 */
@Singleton
@Startup
public class HeartGame {
    
    final double CHANGE_RATE = 0.25;
    List<Heart> hearts = null; // declare hearts to hold Heart entities 
    @Inject
    private HeartFacade heartFacade;
    
    @PostConstruct
public void go() {
    new Thread(new Runnable() {
        public void run() {
            
            // the game runs indefinitely
            while (true) {
                //update all the hearts and save changes to the database
                hearts = heartFacade.findAll();
                for (Heart heart : hearts) {
                    heart.advanceOneTimeIncrement();
                    heartFacade.edit(heart);
                }
                //sleep while waiting to process the next frame of the animation
                try {
                    // wake up roughly CHANGE_RATE times per second
                    Thread.sleep((long)(1.0/CHANGE_RATE*1000));                               
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }).start();
}
/*
*Called from JavaEE8Resource when creating a new heart
*/
public Heart createHeart(Heart heart) {      
        heartFacade.create(heart);
        return heart;
    }
/*
*   Passes an id from JavaEE8Resource file
*/
public Heart getHeartById(int id) {
    for (Heart heart : hearts) {
        if (heart.getId() == id) {
            return heart;
        }
    }
    return null;
}
/*
*Update an existing heart to new values and validate fo duplicate ID errors in the case of creation
*/
public void updateHeart(Heart updatedHeart) {
  for (int i = 0; i < hearts.size(); i++) {
        Heart heart = hearts.get(i);
        if (heart.getId().equals(updatedHeart.getId())) {
            
            // Check if the updated heart will have the same unique or primary key as another existing heart
            for (int j = 0; j < hearts.size(); j++) {
                if (i != j) {
                    Heart otherHeart = hearts.get(j);
                    if (otherHeart.getId().equals(updatedHeart.getId())) {
                        throw new RuntimeException("Heart with id " + updatedHeart.getId() + " already exists");
                    }
                }
            }
            // No duplicate key value found, update the heart
            hearts.set(i, updatedHeart);
            return;
        }
    }
    throw new RuntimeException("Heart with id " + updatedHeart.getId() + " not found");

}


}
