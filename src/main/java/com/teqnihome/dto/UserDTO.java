/**
 * 
 */
package com.teqnihome.dto;

/**
 * @author vkandula
 *
 */

@lombok.Getter
@lombok.Setter
public class UserDTO {
	
	private Long id;
	private String email;
	private String name;
	
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", email=" + email + ", name=" + name + "]";
	}
	
}
