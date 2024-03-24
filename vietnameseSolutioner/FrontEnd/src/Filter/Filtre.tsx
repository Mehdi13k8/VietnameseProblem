import React, { useState } from 'react';

interface FilterProps {
    data: any[];
    onFilter: (filteredData: any[]) => void;
}

const Filter: React.FC<FilterProps> = ({ data, onFilter }) => {
    const [filterValue, setFilterValue] = useState('');

    const handleFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilterValue(event.target.value);
    };

    const handleFilterSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();


        // Filtrer les données en fonction de la valeur saisie
        if (filterValue === '') {
            // Si la valeur du filtre est vide, afficher toutes les données
            onFilter(data);
            return;
        }
        const filteredData = data.filter(item => {
            // if result include filterValue (int to string)
            if (item.result.toString().includes(filterValue)) {
                return true;
            }
        });

        // Appeler la fonction de rappel avec les données filtrées
        onFilter(filteredData);
    };

    return (
        <form onSubmit={handleFilterSubmit} id="filter">
            <input type="text" value={filterValue} onChange={handleFilterChange} placeholder="Filtrer..."
                style={{ width: '90%', padding: '12px 20px', margin: '8px 0', display: 'inline-block', border: '1px solid #ccc', borderRadius: '4px', boxSizing: 'border-box' }}
            />
            <button style={{ width: '10%', backgroundColor: 'lightblue', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
             type="submit">Filtrer</button>
        </form>
    );
};

export default Filter;