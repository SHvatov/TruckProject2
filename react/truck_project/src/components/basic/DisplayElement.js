import React, {Component} from 'react';
import PropTypes from 'prop-types';

/**
 * Basic element, which store the information
 * about the entity, which was fetched from th server.
 * Basic part of the DisplayTable component.
 */
class DisplayElement extends Component {
	static propTypes = {
		/** Array of labels for each field of the entity */
		labels: PropTypes.object,
		/** Entity object, which is stored here */
		entity: PropTypes.object,
		/** Error object */
		error: PropTypes.object,
		/** Status object */
		status: PropTypes.object
	};

	render() {
		let {entity, error, status} = this.props;
		return (
			<div className='row centered-element'>
				{
					Object.keys(entity).map((key) => (
						<div className='col'>

						</div>
					))
				}
			</div>
		);
	}
}

export default DisplayElement;