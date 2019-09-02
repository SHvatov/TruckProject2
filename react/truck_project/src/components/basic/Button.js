import React, {Component} from 'react';
import PropTypes from 'prop-types';

/**
 * Basic button component.
 * @author Sergey Hvatov
 */
class Button extends Component {

	static propTypes = {
		/** Text of the button */
		handleOnClick: PropTypes.func,
		/** Boolean flag */
		isFetching: PropTypes.bool,
		/** Label of the button */
		text: PropTypes.string,
		/** Class of the button */
		clazz: PropTypes.string
	};

	static defaultProps = {
		// fetching status
		isFetching: false
	};

	render() {
		let {
			handleOnClick, isFetching,
			text, clazz
		} = this.props;
		return (
			<button className={clazz}
					type='button'
					onClick={handleOnClick}
					disabled={isFetching}>
				<span className="spinner-border text-light"
					  hidden={!isFetching}/>
				<span hidden={isFetching}>{text}</span>
			</button>
		);
	}
}

export default Button;