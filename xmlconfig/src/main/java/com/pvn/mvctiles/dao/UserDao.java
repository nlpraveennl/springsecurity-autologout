package com.pvn.mvctiles.dao;

import java.util.List;

import com.pvn.mvctiles.model.UserDetails;

public interface UserDao
{

	/**
	 * Method not used in this project
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
	 * @return
	 */
	public List<UserDetails> listUser();
}
