import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {isUndefined} from "../utils/Utils";
import Constants from "../utils/Constants";
import TruckTable from "./containers/trucks/TruckTable";
import DriverTable from "./containers/drivers/DriverTable";
import NavBar from "./containers/tables/NavBar";
import CargoTable from "./containers/cargo/CargoTable";
import {connect} from "react-redux";

/**
 * Describes basic manager user view.
 * @author Sergey Hvatov
 */
class UserFormComponent extends Component {

	static propTypes = {
		// status of the form
		status: PropTypes.object,
	};

	render() {
		let {status} = this.props;
		if (status['current'] === Constants.TRUCKS_TABLE) {
			return (
				<div>
					<NavBar/>
					<TruckTable/>
				</div>
			)
		} else if (status['current'] === Constants.DRIVERS_TABLE) {
			return (
				<div>
					<NavBar/>
					<DriverTable/>
				</div>
			)
		} else if (status['current'] === Constants.CARGO_TABLE) {
			return (
				<div>
					<NavBar/>
					<CargoTable/>
				</div>
			)
		} else {
			return (
				<div>
				</div>
			)
		}
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		status: state.status['manager']
	};
};

const UserForm = connect(mapStateToProps)(UserFormComponent);

export default UserForm;