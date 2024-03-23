import React from 'react';
import './App.css';
import ResultsService from './Services/ResultsService';
import Filter from './Filter/Filtre';

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
    <div className="App">
      <header className="App-header">
      </header>

      <button onClick={loadCalculationResult}>Load Calculation Result</button>

      <br />
      <label htmlFor="filter"
      >Filter by result:</label>
      <Filter
        data={resultsCpy} onFilter={(filteredData: any) => {
          setResults(filteredData);
          setRefreshPage(true);
        }} />

      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Result</th>
            <th>Duration</th>
            <th>calculation</th>
            <th>Edit</th>
            <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          {results.map((result: any) => (
            <tr key={result.id}>
              <td>{result.id}</td>
              <td><input type="text" value={result.result} /> </td>
              <td>{(result.time / 1000000).toFixed(2)}</td>
              <td><input type="text" value={result.calculation} /> </td>
              <td> <button id={result.id}

              >edit</button> </td>
              <td> <button>delete</button> </td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}

export default App;
