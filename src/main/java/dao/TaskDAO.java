package dao;

import domain.Flat;
import domain.Task;

import java.util.Collection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author haydenaish
 */
public interface TaskDAO {
    void createTask(Task task);
    void setCompleteTask(Task task,Boolean completed);
    void setCompleteTask(String taskID,Boolean completed);
    void removeTask(Task task);
    void removeTask(String taskID);
    Task getTask(String taskID);
    Collection<Task> getTaskByFlat(String flatID);
    Collection<Task> getTaskByFlat(Flat flat);
    Collection<Task> getTaskByUser(String userID);
    
}