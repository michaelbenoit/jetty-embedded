package de.bensoft.jettyembedded.employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by michaelbenoit on 02.12.15.
 */
@Path("employees")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class EmployeesResource {

    @Inject
    private EntityManager entityManager;

    @GET
    @Path("{id}")
    public Employee find(@PathParam("id") long id) {
        return entityManager.find(Employee.class, id);
    }

    @GET
    public List<Employee> all() {
        return entityManager
                .createQuery("SELECT e FROM Employee e")
                .getResultList();
    }

    @POST
    public Response create(Employee employee) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(employee);
            entityManager.getTransaction().commit();

            return Response
                    .seeOther(new URI("employees/" + employee.getId()))
                    .build();

        } catch (Throwable e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Unable to create", e);
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") long id, Employee employee) {
        try {

            final Employee employeeToUpdate = entityManager.find(Employee.class, id);
            if (employeeToUpdate == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            entityManager.getTransaction().begin();
            entityManager.merge(employee);
            entityManager.getTransaction().commit();

            return Response
                    .seeOther(new URI("employees/" + employee.getId()))
                    .build();

        } catch (Throwable e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Unable to update", e);
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        try {

            final Employee employeeToDelete = entityManager.find(Employee.class, id);
            if (employeeToDelete == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            entityManager.getTransaction().begin();
            entityManager.remove(employeeToDelete);
            entityManager.getTransaction().commit();

            return Response.ok().build();

        } catch (Throwable e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Unable to update", e);
        }
    }
}
