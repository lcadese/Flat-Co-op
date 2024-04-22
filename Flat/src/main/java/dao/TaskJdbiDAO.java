package dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import domain.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
/**
 *
 * @author haydenaish
 */

public interface TaskJdbiDAO extends TaskDAO{

    @Override
    @SqlUpdate("UPDATE task SET completed = true WHERE taskID = :task.taskID")
    void completeTask(@Bind("task") Task task);

    @Override
    @SqlUpdate("INSERT INTO task (taskID, taskName, Description, FlatID, RequestedDate, completed) values (:task.taskID,:task.taskName, :task.description, :task.FlatID, :task.date, :task.completed)")
    void createTask(@Bind("task") Task task);
}
