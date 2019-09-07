import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Table from "../tables/Table";
import {connect} from "react-redux";
import {addEntity, deleteEntity, fetchEntityList, updateEntity} from "../../../state/Requests";
import Constants from "../../../utils/Constants";
import DriverAdd from "./DriverAdd";
import DriverEdit from "./DriverEdit";

class DriverTableComponent extends Component {

	static propTypes = {
		// user's auth credentials
		credentials: PropTypes.object,

		//  function, which is called to add trucks entities
		addDriverEntity: PropTypes.func,
		//  function, which is called to update trucks entities
		updateDriverEntity: PropTypes.func,
		//  function, which is called to delete trucks entities
		deleteDriverEntity: PropTypes.func,
		//  function, which is called to fetch trucks entities
		fetchDriverEntities: PropTypes.func,
	};

	render() {
		let {
			credentials, fetchDriverEntities, addDriverEntity,
			deleteDriverEntity, updateDriverEntity
		} = this.props;
		return (
			<Table refreshTable={() => fetchDriverEntities(credentials)}
				   addEntity={(entity) => addDriverEntity(entity, credentials)}
				   updateEntity={(entity) => updateDriverEntity(entity, credentials)}
				   deleteEntity={(id) => deleteDriverEntity(id, credentials)}
				   addModalBody={<DriverAdd/>} editModalBody={<DriverEdit/>}
				   initialEntity={Constants.DRIVER_ENTITY_INIT_VALUE}
				   postfix={{
					   workedHours: 'hours', latitude: 'lat.', longitude: 'long.'
				   }}/>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		credentials: state.credentials
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		addDriverEntity: (entity, credentials) => dispatch(addEntity(Constants.DRIVER.ADD, entity, credentials)),
		updateDriverEntity: (entity, credentials) => dispatch(updateEntity(Constants.DRIVER.UPDATE, entity, credentials)),
		deleteDriverEntity: (id, credentials) => dispatch(deleteEntity(Constants.DRIVER.DELETE, id, credentials)),
		fetchDriverEntities: (credentials) => dispatch(fetchEntityList(Constants.DRIVER.FETCH, credentials)),
	}
};

const DriverTable = connect(mapStateToProps, mapDispatchToProps)(DriverTableComponent);

export default DriverTable;