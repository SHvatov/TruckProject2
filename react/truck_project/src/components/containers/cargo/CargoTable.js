import React, {Component} from 'react';
import PropTypes from 'prop-types';
import Table from "../tables/Table";
import {connect} from "react-redux";
import {addEntity, deleteEntity, fetchEntityList, updateEntity} from "../../../state/Requests";
import Constants from "../../../utils/Constants";
import CargoAdd from "./CargoAdd";
import CargoEdit from "./CargoEdit";

class CargoTableComponent extends Component {

	static propTypes = {
		// user's auth credentials
		credentials: PropTypes.object,

		//  function, which is called to add trucks entities
		addCargoEntity: PropTypes.func,
		//  function, which is called to update trucks entities
		updateCargoEntity: PropTypes.func,
		//  function, which is called to delete trucks entities
		deleteCargoEntity: PropTypes.func,
		//  function, which is called to fetch trucks entities
		fetchCargoEntities: PropTypes.func,
	};

	render() {
		let {
			credentials, fetchCargoEntities, addCargoEntity,
			deleteCargoEntity, updateCargoEntity
		} = this.props;
		return (
			<Table refreshTable={() => fetchCargoEntities(credentials)}
				   addEntity={(entity) => addCargoEntity(entity, credentials)}
				   updateEntity={(entity) => updateCargoEntity(entity, credentials)}
				   deleteEntity={(id) => deleteCargoEntity(id, credentials)}
				   addModalBody={<CargoAdd/>} editModalBody={<CargoEdit/>}
				   initialEntity={Constants.CARGO_ENTITY_INIT_VALUE}
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
		addCargoEntity: (entity, credentials) => dispatch(addEntity(Constants.CARGO.ADD, entity, credentials)),
		updateCargoEntity: (entity, credentials) => dispatch(updateEntity(Constants.CARGO.UPDATE, entity, credentials)),
		deleteCargoEntity: (id, credentials) => dispatch(deleteEntity(Constants.CARGO.DELETE, id, credentials)),
		fetchCargoEntities: (credentials) => dispatch(fetchEntityList(Constants.CARGO.FETCH, credentials)),
	}
};

const CargoTable = connect(mapStateToProps, mapDispatchToProps)(CargoTableComponent);

export default CargoTable;