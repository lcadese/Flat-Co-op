package dao;

import domain.Task;

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
    void completeTask(Task task);
    
    
}