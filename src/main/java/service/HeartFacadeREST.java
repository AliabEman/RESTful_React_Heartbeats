/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.carr0676.heartbeat.entity.Heart;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
/**
 *
 * @author Dylan
 */
@Stateless
@Path("cst8218.carr0676.heartbeat.entity.heart")
public class HeartFacadeREST extends AbstractFacade<Heart> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public HeartFacadeREST() {
        super(Heart.class);
    }
    /*
    *POST function create 
    */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Heart entity) {
        
        super.create(entity);
        
    }
    /*
    * POST requests to update existing hearts. WIll update non null instances
    */
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    // Response method found on net
    public Response editPost(@PathParam("id") Long id, Heart entity) {
        
   
        
        try {
            Heart sprite = super.find(id);
            if ( sprite == null ) {
                throw new Exception ("not found");
            }
            
            if ( sprite.getId().equals(id) ) {
                super.edit(entity);
            } else {
                throw new Exception ("no id");
            }
            
            entity.setId(id);
            super.edit(entity);
            
            return Response.status(Response.Status.OK).build(); 
            //return Response.status(Response.Status.OK).entity(sprite).build(); //debug   
        } catch (Exception e) {
            return Response.serverError().build();
        }   
    }
    /*
    * PUT requests to update existing hearts. Will overwrite the entity
    */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response.ResponseBuilder editHeart(@PathParam("id") Long id, Heart entity) {
        try{
            Heart heart = super.find(id);
            if(entity.getId().equals(id)){
                super.edit(entity);
                entity.setId(id);
               
                Response.status(Response.Status.OK).entity(entity).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND);
               
               
            }
        }catch(Exception e){
           System.out.println(e);
        }
        return null;
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Heart find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Heart> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
