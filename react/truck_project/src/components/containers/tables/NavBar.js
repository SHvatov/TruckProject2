import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Button, Col, Row} from "react-bootstrap";
import {connect} from "react-redux";
import {updateStatusById} from "../../../state/Status";
import Constants from "../../../utils/Constants";
import {isUndefined} from "../../../utils/Utils";
import {fetchEntityList, multipleActions} from "../../../state/Requests";
import {clearData} from "../../../state/Data";
import {validateCredentialsRequestSuccess} from "../../../state/Credentials";

/**
 * Describes basic navigation bar for the manger user.
 * @author Sergey Hvatov
 */
class NavBarComponent extends Component {

	static propTypes = {
		// application status
		status: PropTypes.object,
		// user's credentials
		credentials: PropTypes.object,
		// function, which is called to change the status of the
		// manager view
		changeStatus: PropTypes.func,
		// function called on logout
		logout: PropTypes.func
	};

	componentDidMount() {
		this.props.changeStatus(Constants.TRUCK.FETCH, {}, this.props.credentials)
	}

	render() {
		let {status, changeStatus, logout} = this.props;
		return (
			<div className='table'>
				<Row className='table-row'>
					<Col className='table-button'>
						<Button
							variant={status['current'] === Constants.TRUCKS_TABLE ? "primary" : "outline-primary"}
							size='lg' style={{width: '100%'}}
							onClick={() => changeStatus(
								Constants.TRUCK.FETCH,
								{current: Constants.TRUCKS_TABLE},
								this.props.credentials
							)}>
							Trucks
						</Button>
					</Col>
					<Col className='table-button'>
						<Button variant={status['current'] === Constants.DRIVERS_TABLE ? "primary" : "outline-primary"}
								size='lg' style={{width: '100%'}}
								onClick={() => changeStatus(
									Constants.DRIVER.FETCH,
									{current: Constants.DRIVERS_TABLE},
									this.props.credentials
								)}>
							Drivers
						</Button>
					</Col>
					<Col className='table-button'>
						<Button variant={status['current'] === Constants.CARGO_TABLE ? "primary" : "outline-primary"}
								size='lg' style={{width: '100%'}}
								onClick={() => changeStatus(
									Constants.CARGO.FETCH,
									{current: Constants.CARGO_TABLE},
									this.props.credentials
								)}>
							Cargo
						</Button>
					</Col>
					<Col className='table-button'>
						<Button variant={status['current'] === Constants.ORDERS_TABLE ? "primary" : "outline-primary"}
								size='lg' style={{width: '100%'}}
								onClick={() => changeStatus(
									Constants.ORDER.FETCH,
									{current: Constants.ORDERS_TABLE},
									this.props.credentials
								)}>
							Orders
						</Button>
					</Col>
					<Col className='table-button'>
						<Button variant="danger" size='lg' style={{width: '100%'}}
								onClick={logout}>Logout</Button>
					</Col>
				</Row>
			</div>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		status: state.status['manager'],
		credentials: state.credentials
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		changeStatus: (url, newStatus, credentials) => dispatch(multipleActions([
			updateStatusById('manager', newStatus),
			clearData(),
			fetchEntityList(url, credentials)
		])),
		logout: () => dispatch(validateCredentialsRequestSuccess({
			id: '',
			password: '',
			authority: Constants.NOT_AUTHORIZED
		}))
	}
};

const NavBar = connect(mapStateToProps, mapDispatchToProps)(NavBarComponent);

export default NavBar;