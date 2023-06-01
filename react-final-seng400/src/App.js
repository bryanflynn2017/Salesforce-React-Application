import React, { useState } from 'react';
import './App.css';
import AddCareer from './AddCareer';
import EditCareer from './EditCareer';
import DeleteCareer from './DeleteCareer';
import FindCareer from './FindCareer';

function App() {
	const [currentPage, setCurrentPage] = useState('');

	const handleAddCareerClick = () => {
		setCurrentPage('addCareer');
	};

	const handleEditCareerClick = () => {
		setCurrentPage('editCareer');
	};

	const handleDeleteCareerClick = () => {
		setCurrentPage('deleteCareer');
	};

	const handleFindCareerClick = () => {
		setCurrentPage('findCareer');
	};
	
	const handleGoHome = () => {
		setCurrentPage('goHome');
	};

	const renderPage = () => {
		switch (currentPage) {
			case 'addCareer':
				return <AddCareer />;
			case 'editCareer':
				return <EditCareer />;
			case 'deleteCareer':
				return <DeleteCareer />;
			case 'findCareer':
				return <FindCareer />;
			case 'goHome':
				return <App />;
			default:
				return (
					<div>
						<h1>Career Management App</h1>
						<button onClick={handleFindCareerClick}>Find Career</button>
						<button onClick={handleAddCareerClick}>Add Career</button>
						<button onClick={handleEditCareerClick}>Edit Career</button>
						<button onClick={handleDeleteCareerClick}>Delete Career</button>
					</div>
				);
		}
	};

	return (
		<div className="App">
			<header className="App-header">{renderPage()}</header>
		</div>
	);
}

export default App;
