/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.carr0676.heartbeat.entity;

import cst8218.carr0676.heartbeat.entity.Heart;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Dylan
 */
@Stateless
public class HeartFacade extends AbstractFacade<Heart> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HeartFacade() {
        super(Heart.class);
    }
    
}
