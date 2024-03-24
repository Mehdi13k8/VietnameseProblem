import React from 'react';
import './App.css';
import ResultsService from './Services/ResultsService';
import Filter from './Filter/Filtre';

// route
import { Route, BrowserRouter, Routes } from 'react-router-dom';
import Result from './Result/Result';
import MainPage from './MainPage/MainPage';

// BrowserRouter for routing /result/${result.id}
// Route for /result/${result.id}




function App() {

  const [results, setResults] = React.useState([]);
  const [resultsCpy, setResultsCpy] = React.useState([]);
  const [refreshPage, setRefreshPage] = React.useState(false);

  const refreshResults = () => {
    ResultsService.getResults().then((data: any) => {
      setResults(data);
      setResultsCpy(data);
    })
      .catch((error: any) => {
        console.error('Error:', "data ==>", error);
      });
  };

  React.useEffect(() => {
    refreshResults();
  }, []);

  React.useEffect(() => {
    if (refreshPage) {
      setRefreshPage(false);
    }
  }, [refreshPage]);


  const loadCalculationResult = () => {
    setResults([]);
    ResultsService.loadCalculationResult({})
      .then((data: any) => {
      })
      .catch((error: any) => {
        console.error('Error:', "data ==>", error);
      })
      .finally(() => {
        refreshResults();
      });
  };

  const lineEdit = (id: any) => {
    console.log("id ==>", id);
  }

  return (
    <BrowserRouter basename="/">
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/result/:id" element={<Result />} />
      </Routes>
    </BrowserRouter >
  );
}

export default App;
