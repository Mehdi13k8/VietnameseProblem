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
            return item.result == filterValue;
        });

        // Appeler la fonction de rappel avec les données filtrées
        onFilter(filteredData);
    };

    return (
        <form onSubmit={handleFilterSubmit} id="filter">
            <input type="text" value={filterValue} onChange={handleFilterChange} placeholder="Filtrer..." />
            <button type="submit">Filtrer</button>
        </form>
    );
};

export default Filter;