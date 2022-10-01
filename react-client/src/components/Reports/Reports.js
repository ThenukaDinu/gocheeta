import React from 'react';
import { Doughnut } from 'react-chartjs-2';
import './Reports.scss';

export default function Reports() {
  return (
    <div className='reports'>
      <div>
        <Doughnut
          data={{
            labels: ['Jun', 'Jul', 'Aug'],
            datasets: [
              {
                id: 1,
                label: '',
                data: [5, 6, 7],
              },
            ],
            hoverOffset: 4,
          }}
        />
      </div>
    </div>
  );
}
