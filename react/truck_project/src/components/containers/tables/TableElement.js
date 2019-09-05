import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {convertObjectToColumns} from "../../../utils/Utils";
import {Col, Row} from "react-bootstrap";

/**
 * Basic element, which store the information
 * about the entity, which was fetched from the server.
 */
class TableElement extends Component {

	static propTypes = {
		// entity stored in this container
		entity: PropTypes.object,
		// map of text messages, that are
		// displayed after associated fields of the entity
		postfix: PropTypes.object
	};

	render() {
		let {entity, postfix} = this.props;
		return (
			<Row className='table-row centered-element'>
				{convertObjectToColumns(entity, postfix)}
				{
					!this.props.children ? '' : React.Children.map(this.props.children, (child) => (
						<Col className='table-button' xs={1}>
							{child}
						</Col>
					))
				}
			</Row>
		);
	}
}

export default TableElement;