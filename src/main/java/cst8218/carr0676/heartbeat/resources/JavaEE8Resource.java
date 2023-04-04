package cst8218.carr0676.heartbeat.resources;
 
import cst8218.carr0676.heartbeat.entity.HeartFacade;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import cst8218.carr0676.heartbeat.entity.Heart;
import cst8218.carr0676.heatbeat.presentation.HeartGame;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import java.net.URI;
import javax.ws.rs.PUT;
import javax.validation.Valid;
import javax.ws.rs.PathParam;
/**
 *
 * @author Dylan Carroll
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @Inject
    private HeartFacade heartFacade;
    private HeartGame heartService;
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
    /*
    * Count heart entities by making a list of them then getting its size
    */
    @GET
    @Path("count")
    public Response getHeartCount() {
    List<Heart> hearts;      // create list
    hearts = heartFacade.findAll();  // fill list with heart instances
    int count = hearts.size(); // find how many are in the list for total
    return Response.status(Response.Status.OK).entity(count).build(); // I realize I dont need this cause its generated but whatever
   
    }
    
/*
* Checks if the heart exists, if it does it then modifys it and if not 
*/
@POST
@Consumes(MediaType.APPLICATION_JSON)
public Response createHeart(@Valid Heart heart, @Context UriInfo uriInfo) {
       Heart existingHeart = heartService.getHeartById(heart.getId().intValue());
    if (existingHeart != null) {
        
        existingHeart.setBeatCount(heart.getBeatCount());
        existingHeart.setSize(heart.getSize());
        existingHeart.setContractedSize(heart.getContractedSize());
        heartService.updateHeart(existingHeart);
        return Response.status(Response.Status.OK).entity(existingHeart).build();
    } else {// no ID for that heart or no heart is at that ID then should be good to create a new one
        Heart newHeart = heartService.createHeart(heart);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newHeart.getId().toString()).build();
        return Response.created(uri).entity(newHeart).build();
    }
}
/*
*Throw error during a PUT on the root resource table
*/
@PUT
public Response updateSpriteTable() {
    return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
}

    

/*
*   Specified ID put request on existing heart, bad request if non existing ID
*
*
*/

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHeart(@Valid Heart updatedHeart, @PathParam("id") Long id) {
        Heart existingHeart = heartService.getHeartById(updatedHeart.getId().intValue());
        
        if (existingHeart == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
            
        } else {
            // Check if the updated heart has a non-matching id
            if (!existingHeart.getId().equals(updatedHeart.getId())) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            // Set default values for properties that cannot be null and are missing from the updatedHeart        
  
                updatedHeart.setBeatCount(existingHeart.getBeatCount());
            

                updatedHeart.setSize(existingHeart.getSize());
            
                updatedHeart.setContractedSize(existingHeart.getContractedSize());
            

            // Update the existing heart with the new values
            existingHeart.setBeatCount(updatedHeart.getBeatCount());
            existingHeart.setSize(updatedHeart.getSize());
            existingHeart.setContractedSize(updatedHeart.getContractedSize());
            heartService.updateHeart(existingHeart);
            return Response.status(Response.Status.OK).entity(existingHeart).build();
        }
    }
}

