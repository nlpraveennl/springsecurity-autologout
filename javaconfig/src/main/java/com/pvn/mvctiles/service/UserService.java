package com.pvn.mvctiles.service;

import java.util.List;

import com.pvn.mvctiles.model.UserDetails;

public interface UserService
{

	/**
	 * @param userDetails
	 * @return
	 */
	public UserDetails authenticateUser(UserDetails userDetails);
	
	/**
	 * @param userDetails
	 * @return
	 */
	public UserDetails addUser(UserDetails userDetails);
	
	/**
	 * @param userDetails
	 * @return
	 */
	public UserDetails modifyUser(UserDetails userDetails);
	
	/**
	 * @param userDetails
	 * @return
	 */
	public List<UserDetails> listUser();

}
