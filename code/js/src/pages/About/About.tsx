import React from 'react';

const About = () => {
    return (
        <div style={{ textAlign: 'center', padding: '20px' }}>
            <h1 style={{ fontSize: '2em', marginBottom: '20px' }}>About Page</h1>
            <div className="name-row" style={{ display: 'flex', justifyContent: 'center' }}>
                <div style={{ margin: '0 10px' }}>
                    <p style={{ fontSize: '1.2em' }}>Daniel 46052</p>
                </div>
                <div style={{ margin: '0 10px' }}>
                    <p style={{ fontSize: '1.2em' }}>Diogo 48459</p>
                </div>
                <div style={{ margin: '0 10px' }}>
                    <p style={{ fontSize: '1.2em' }}>André 47224</p>
                </div>
            </div>
            <div style={{ marginTop: '20px' }}>
                <p style={{ fontSize: '1em' }}>
                    Gomoku is a classic strategy board game that involves two players taking turns
                    placing their pieces on a board. The objective is to create a row of five
                    consecutive pieces either horizontally, vertically, or diagonally.
                </p>
                <p style={{ fontSize: '1em', marginTop: '10px' }}>
                    For more information, check out the official{' '}
                    <a
                        href="https://en.wikipedia.org/wiki/Gomoku"
                        target="_blank"
                        rel="noopener noreferrer"
                        style={{ textDecoration: 'underline', color: '#007BFF', fontSize: '1.2em', transition: 'color 0.3s' }}
                    >
                        {''}
                        Gomoku Wiki
                    </a>
                    .
                </p>
            </div>
        </div>
    );
};

export default About;
