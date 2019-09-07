import React, {Component} from 'react';
import LoginForm from "./components/LoginForm";
import {connect} from "react-redux";
import * as PropTypes from "prop-types";
import Constants from "./utils/Constants";
import UserForm from "./components/UserForm";

/**
 * Main entry point of the application.
 * @author Sergey Hvatov
 */
class AppComponent extends Component {

	static propTypes = {
		/** Credentials of the user */
		credentials: PropTypes.shape({
			id: PropTypes.string,
			username: PropTypes.string,
			authority: PropTypes.string
		}),
	};

	render() {
		let {credentials} = this.props;
		switch (credentials.authority) {
			default:
			case Constants.NOT_AUTHORIZED:
				return (
					<div>
						<LoginForm/>
					</div>
				);
			case Constants.MANAGER_USER:
				return (
					<div>
						<UserForm/>
					</div>
				);
			case Constants.DRIVER_USER:
				return (
					<div>
					</div>
				);
		}
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

const App = connect(mapStateToProps)(AppComponent);

export default App;