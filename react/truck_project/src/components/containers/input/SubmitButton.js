import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Button, Spinner} from "react-bootstrap";

/**
 * Button, constructed using bootstrap components.
 * @author Sergey Hvatov
 */
class SubmitButton extends Component {

	static propTypes = {
		/** Request status */
		isFetching: PropTypes.bool,
		/** On click function */
		onClickHandler: PropTypes.func,
		/** Button text */
		text: PropTypes.string,
		// type of the button
		variant: PropTypes.string
	};

	render() {
		let {isFetching, onClickHandler, text, variant = 'primary'} = this.props;
		return (
			<Button variant={variant} disabled={isFetching}
					type='submit' onClick={onClickHandler}>
				<Spinner as="span" animation="border"
						 size="sm" role="status"
						 aria-hidden="true" hidden={!isFetching}/>
				{isFetching ? 'Loading...' : text}
			</Button>
		);
	}
}

export default SubmitButton;