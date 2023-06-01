import React, { useState } from 'react';
import App from './App';

function EditCareer() {
	const [currentPage, setCurrentPage] = useState('');
	
	const [careerId, setCareerId] = useState("");
	const [careerName, setCareerName] = useState("");
	const [companyName, setCompanyName] = useState("");
	const [position, setPosition] = useState("");
	const [dateStarted, setDateStarted] = useState("");

	function handleEditCareer() {
		fetch(`http://localhost:8080/springtosalesforce/careers`, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				sfId: careerId,
				careerName: careerName,
				companyName: companyName,
				position: position,
				dateStarted: dateStarted,
			}),
		})
			.then((response) => response.json())
			.then((result) => {
				alert(result.message);
			});
	}
	
	function handleCareerIdChange(event) {
		setCareerId(event.target.value);
	}

	function handleCareerNameChange(event) {
		setCareerName(event.target.value);
	}

	function handleCompanyNameChange(event) {
		setCompanyName(event.target.value);
	}

	function handlePositionChange(event) {
		setPosition(event.target.value);
	}

	function handleDateStartedChange(event) {
		setDateStarted(event.target.value);
	}
	
	const handleGoHome = () => {
		setCurrentPage('goHome');
	};
	
	const renderPage = () => {
		switch (currentPage) {
			case 'goHome':
				return <App />;
			default:
				return (
					<div>
						<button onClick={handleGoHome}>Return to Actions</button>
					</div>
				);
		}
	};
	
	return (
		<div className="App">
			<main>
				<section>
					<h2>Edit Career</h2>
					<label>
						Career ID:
						<input type="text" value={careerId} onChange={handleCareerIdChange} />
					</label>
					<br />
					<label>
						Position:
						<input type="text" value={position} onChange={handlePositionChange} />
					</label>
					<br />
					<button onClick={handleEditCareer}>Save</button>
				</section>{renderPage()}
				</main>
		</div>
	);
}

export default EditCareer;