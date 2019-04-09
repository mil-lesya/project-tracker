package com.gmail.ivanjermakov1.projecttracker.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gmail.ivanjermakov1.projecttracker.core.entity.enums.TaskType;

import java.time.LocalDateTime;
import java.util.List;

public class TaskDto {
	
	public Long id;
	public ProjectDto project;
	public TaskType type;
	public Double estimate;
	public Double elapsed;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime opened;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public LocalDateTime due;
	public String name;
	public String description;
	public List<TaskDto> subtasks;
	
	public TaskDto() {
	}
	
	public TaskDto(Long id, ProjectDto project, TaskType type, Double estimate, Double elapsed, LocalDateTime opened, LocalDateTime due, String name, String description, List<TaskDto> subtasks) {
		this.id = id;
		this.project = project;
		this.type = type;
		this.estimate = estimate;
		this.elapsed = elapsed;
		this.opened = opened;
		this.due = due;
		this.name = name;
		this.description = description;
		this.subtasks = subtasks;
	}
	
}