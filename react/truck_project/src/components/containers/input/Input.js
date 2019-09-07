import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Col, Form, FormGroup, InputGroup} from "react-bootstrap";
import {updateBuffer} from "../../../state/Buffer";
import {connect} from "react-redux";

/**
 * Input group, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class InputComponent extends Component {

	static propTypes = {
		/** Name of the input */
		name: PropTypes.string,
		/** Label of the input */
		label: PropTypes.string,
		// label, which is displayed in
		// the end of the input line
		postLabel: PropTypes.string,
		/** Type of the input */
		type: PropTypes.string,

		/** Corresponding error */
		error: PropTypes.object,
		// application buffer, where all input is stored
		buffer: PropTypes.object,
		/** Update handler */
		handleValueUpdate: PropTypes.func
	};

	render() {
		let {
			name, label, error, type = 'text',
			buffer, handleValueUpdate, postLabel = ''
		} = this.props;
		return (
			<Form.Row>
				<FormGroup as={Col}>
					<InputGroup>
						<InputGroup.Prepend>
							<InputGroup.Text id={name + 'Prepend'}>{label}</InputGroup.Text>
						</InputGroup.Prepend>
						{
							postLabel ? (
								<InputGroup.Prepend>
									<InputGroup.Text id={name + 'Prepend'}>{postLabel}</InputGroup.Text>
								</InputGroup.Prepend>
							) : ''
						}
						<Form.Control type={type} aria-describedby={name + 'Prepend'}
									  name={name} value={buffer[name] ? buffer[name] : ''}
									  onChange={(event) => handleValueUpdate(name, event.target.value)}
									  isInvalid={error[name]}/>
						<Form.Control.Feedback type="invalid">
							{error[name]}
						</Form.Control.Feedback>
					</InputGroup>
				</FormGroup>
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
		error: state.error
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

const Input = connect(mapStateToProps, mapDispatchToProps)(InputComponent);

export default Input;