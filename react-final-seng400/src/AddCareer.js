import React, { useState } from 'react';
import App from './App';

function AddCareer() {
	const [currentPage, setCurrentPage] = useState('');
	
	const [careerName, setCareerName] = useState("");
	const [companyName, setCompanyName] = useState("");
	const [position, setPosition] = useState("");
	const [dateStarted, setDateStarted] = useState("");

	function handleAddCareer() {
		fetch(`http://localhost:8080/springtosalesforce/careers`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
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
					<h2>Add Career</h2>
					<label>
						Career Name:
						<input type="text" value={careerName} onChange={handleCareerNameChange} />
					</label>
					<br />
					<label>
						Company Name:
						<input type="text" value={companyName} onChange={handleCompanyNameChange} />
					</label>
					<br />
					<label>
						Position:
						<input type="text" value={position} onChange={handlePositionChange} />
					</label>
					<br />
					<label>
						Date Started:
						<input type="text" value={dateStarted} onChange={handleDateStartedChange} />
					</label>
					<br />
					<button onClick={handleAddCareer}>Add</button>
				</section>{renderPage()}
			</main>
		</div>
	);
}

export default AddCareer;