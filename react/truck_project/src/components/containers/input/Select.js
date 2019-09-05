import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Col, Form, InputGroup} from 'react-bootstrap';
import {updateBuffer} from "../../../state/Buffer";
import {connect} from "react-redux";

/**
 * Selector, constructed from bootstrap components.
 * @author Sergey Hvatov
 */
class SelectComponent extends Component {

	static propTypes = {
		// select label
		label: PropTypes.string,
		// select name
		name: PropTypes.string,
		// options for the select
		options: PropTypes.arrayOf(PropTypes.shape({
			value: PropTypes.any,
			label: PropTypes.string
		})),

		/** Update handler */
		handleValueUpdate: PropTypes.func
	};

	render() {
		let {label, name, options, handleValueUpdate} = this.props;
		return (
			<Form.Row>
				<Form.Group as={Col}>
					<InputGroup>
						<InputGroup.Prepend>
							<InputGroup.Text id={name + 'Prepend'}>{label}</InputGroup.Text>
						</InputGroup.Prepend>
						<Form.Control as="select" onChange={(event) => handleValueUpdate(name, event.target.value)}>
							{
								options.map((opt, index) => (
									<option key={index} value={opt.value}>
										{opt.label}
									</option>
								))
							}
						</Form.Control>
					</InputGroup>

				</Form.Group>
			</Form.Row>
		);
	}
}

/**
 * Maps state state to react properties.
 * @param state - state instance.
 */
const mapStateToProps = state => {
	return {
		buffer: state.buffer,
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {
		handleValueUpdate: (field, value) => dispatch(updateBuffer({[field]: value}))
	}
};

const Select = connect(mapStateToProps, mapDispatchToProps)(SelectComponent);

export default Select;