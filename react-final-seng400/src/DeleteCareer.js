import React, { useState } from "react";
import App from './App';

function DeleteCareer() {
	const [currentPage, setCurrentPage] = useState('');
	
	const [careerId, setCareerId] = useState("");
	
	function handleDeleteCareer() {
		fetch(`http://localhost:8080/springtosalesforce/careers/${careerId}`, {
			method: "DELETE",
		})
			.then((response) => response.json())
			.then((result) => {
				alert(result.message);
			});
	}
	
	function handleCareerIdChange(event) {
		setCareerId(event.target.value);
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
					<h2>Delete Career</h2>
					<label>
						Career ID:
						<input type="text" value={careerId} onChange={handleCareerIdChange} />
					</label>
					<button onClick={handleDeleteCareer}>Delete</button>
				</section>{renderPage()}
			</main>
		</div>
	);
}

export default DeleteCareer;