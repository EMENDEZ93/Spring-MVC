package com.springcourse.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import com.springcourse.pojo.Admin;
import com.springcourse.pojo.AdminRowMapper;

@Component("adminDao")
public class AdminDaoImplement implements AdminDao {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public boolean save(Admin admin) {
		//MapSqlParameterSource paraMap = new MapSqlParameterSource();
		//paraMap.addValue("nombre", admin.getNombre());
		//paraMap.addValue("cargo", admin.getCargo());
		//paraMap.addValue("fechaCreacion", admin.getFechaCreacion());

		BeanPropertySqlParameterSource paraMap = new BeanPropertySqlParameterSource(admin);
		
		return jdbcTemplate.
				update("insert into admin (nombre, cargo, fechaCreacion) values"
						+ " (:nombre, :cargo, :fechaCreacion)", paraMap) == 1;
	}

	@Override
	public List<Admin> findAll() {
		return jdbcTemplate.query("select * from admin", new RowMapper<Admin>() {

			@Override
			public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
				Admin admin = new Admin();
				admin.setIdAd(rs.getInt("idAd"));
				admin.setCargo(rs.getString("cargo"));
				admin.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
				admin.setNombre(rs.getString("nombre"));
				return admin;
			}
		});
	}

	@Override
	public Admin finById(int id) {
		//return (Admin) jdbcTemplate.query("select * from admin where idAd=:idAd",
				//new MapSqlParameterSource("idAd",id) , new AdminRowMapper() );
	
		return jdbcTemplate.queryForObject("select * from admin where idAd=:idAd",
				new MapSqlParameterSource("idAd",id) , new AdminRowMapper());
	}

	@Override
	public List<Admin> findByNombre(String nombre) {
		return jdbcTemplate.query("select * from admin where nombre like :nombre",
				new MapSqlParameterSource("nombre","%"+nombre+"%") , new AdminRowMapper());
	}

	@Override
	public boolean update(Admin admin) {
		return jdbcTemplate.update("update admin set nombre=:nombre, cargo=:cargo, fechaCreacion=:fechaCreacion where idAd=:idAd", 
				new BeanPropertySqlParameterSource(admin)) == 1;
	}

	@Override
	public boolean delete(int idAd) {
		return jdbcTemplate.update("delete from admin where idAd=:idAd", new MapSqlParameterSource("idAd", idAd)) ==1;
	}

	@Transactional
	@Override
	public int[] saveAll(List<Admin> admins) {
		SqlParameterSource[] batchArgs = SqlParameterSourceUtils.createBatch(admins.toArray()); 
		
		return jdbcTemplate.batchUpdate("insert into admin (nombre, cargo, fechaCreacion) values (:nombre, :cargo, :fechaCreacion)",
				batchArgs);
	}
	
}
