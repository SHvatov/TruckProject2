import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from "react-redux";
import {isEmpty} from "../../../utils/Utils";
import {Alert, Col, Form, FormGroup, Row} from "react-bootstrap";
import SubmitButton from "../input/SubmitButton";
import TableElement from "./TableElement";
import AddModal from "../modals/AddModal";
import EditModal from "../modals/EditModal";
import DeleteModal from "../modals/DeleteModal";
import ShowMapModal from "../modals/ShowMapModal";

/**
 * Basic element, which store the information
 * about all entities, that were fetched from the server.
 */
class TableComponent extends Component {

	static propTypes = {
		// function, which is called to fetch entities list
		refreshTable: PropTypes.func,
		// function, which is called to add entity
		addEntity: PropTypes.func,
		// function, which is called to update entity
		updateEntity: PropTypes.func,
		// function, which is called to delete entity
		deleteEntity: PropTypes.func,
		// postfix text messages
		postfix: PropTypes.object,
		// describes the body of the add modal
		addModalBody: PropTypes.object,
		// describes the body of the edit modal
		editModalBody: PropTypes.object,

		// application data
		data: PropTypes.object,
		// status of the application
		status: PropTypes.object,
		// error object
		error: PropTypes.object
	};

	componentDidMount() {
		// fetch values from the server
		this.props.refreshTable();
	}

	render() {
		let {
			status, error, refreshTable, addEntity,
			updateEntity, deleteEntity, data, postfix,
			addModalBody, editModalBody
		} = this.props;
		return (
			<div className='table'>
				<Row className='table-row'>
					<Col xs={1} className='table-button'>
						<SubmitButton onClickHandler={refreshTable} text='Refresh'
									  isFetching={status['table'] ? status['table']['fetch'] : false}/>
					</Col>
					<Col xs={1} className='table-button'>
						<AddModal id='addModal' status={status['addModal']} addEntity={addEntity}>
							{addModalBody}
						</AddModal>
					</Col>
				</Row>
				<Row className='table-row' hidden={!error['tableError']}>
					<Alert variant="danger" hidden={!error['tableError']} className='custom-alert'>
						<strong>Error: </strong>{error['tableError']}
					</Alert>
				</Row>
				{
					isEmpty(data)
						? (
							<Row className='table-row'>
								<Col className='centered-element'>
									<strong>No entities found</strong>
								</Col>
							</Row>
						)
						: Object.keys(data).map((key) => (
							<TableElement entity={data[key]} postfix={postfix}>
								<EditModal status={status[key]} entity={data[key]} updateEntity={updateEntity}>
									{editModalBody}
								</EditModal>
								<DeleteModal status={status[key]} entity={data[key]} deleteEntity={deleteEntity}/>
								<ShowMapModal status={status[key]} entity={data[key]}/>
							</TableElement>
						))
				}
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
		data: state.data,
		status: state.status,
		error: state.error
	};
};

/**
 * Dispatches action creator functions to properties.
 * @param dispatch - dispatcher instance.
 */
const mapDispatchToProps = dispatch => {
	return {}
};

const Table = connect(mapStateToProps, mapDispatchToProps)(TableComponent);

export default Table;