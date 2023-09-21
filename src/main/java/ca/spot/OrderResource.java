package ca.spot;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;


@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    public List<FWOOrder> getAllOrders() {
        return FWOOrder.listAll();
    }

    @GET
    @Path("/customerName/{name}")
    public Response list(@PathParam("name") String name,
                        @QueryParam("page") @DefaultValue("0") int pageIndex,
                        @QueryParam("size") @DefaultValue("20") int pageSize) {

        List<FWOOrder> list = FWOOrder.find("customerName = :customerName", Collections.singletonMap("customerName", name)).list();
        return  Response.status(Response.Status.OK)
                        .entity(list)
                        .build();
    }

    @GET
    @Path("/{id}")
    public FWOOrder getOrder(@PathParam("id") Long id) {
        return FWOOrder.findById(id);
    }

    @POST
    @Transactional
    public Response createOrder(FWOOrder order) {
        order.persist();
        if(order.isPersistent()){
            System.out.println("success");
        }
        String locationUrl = "/orders/" + order.id;
        return  Response.status(Response.Status.CREATED)
                        .header("Location", locationUrl)
                        .entity(order)
                        .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateOrder(@PathParam("id") Long id, FWOOrder order) {
        FWOOrder entity = FWOOrder.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Order with id " + id + " does not exist.", 404);
        }
        entity.customerName = order.customerName;
        entity.orderDate = order.orderDate;
        entity.orderStatus = order.orderStatus;
        entity.persist();
        if(order.isPersistent()){
            System.out.println("success");
        }
        String locationUrl = "/orders/" + entity.id;
        return  Response.status(Response.Status.OK)
                        .header("Location", locationUrl)
                        .entity(entity)
                        .build();
    }
}

