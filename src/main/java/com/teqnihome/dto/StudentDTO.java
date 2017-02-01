/**
 * 
 */
package com.teqnihome.dto;

import com.teqnihome.model.Team;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vkandula
 *
 */

@Getter
@Setter
public class StudentDTO {

	private Long id;
	private String name;
	private Team team;
}
