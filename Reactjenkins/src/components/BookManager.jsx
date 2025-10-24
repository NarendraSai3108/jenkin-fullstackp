import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';
import config from './config.js';

const BookManager = () => {
  const [books, setBooks] = useState([]);
  const [book, setBook] = useState({ id: '', title: '', author: '', genre: '', publisher: '', year: '' });
  const [idToFetch, setIdToFetch] = useState('');
  const [fetchedBook, setFetchedBook] = useState(null);
  const [message, setMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  const baseUrl = `${config.url}`;

  useEffect(() => { fetchAllBooks(); }, []);

  const fetchAllBooks = async () => {
    try { const res = await axios.get(`${baseUrl}/all`); setBooks(res.data); } 
    catch { setMessage('Failed to fetch books.'); }
  };

  const handleChange = (e) => {
    const value = e.target.name === 'year' ? e.target.value.toString() : e.target.value;
    setBook({ ...book, [e.target.name]: value });
  };

  const validateForm = () => {
    for (let key in book) {
      if (key === 'id') continue;
      if (!book[key] || book[key].toString().trim() === '') {
        setMessage(`Please fill out the ${key} field.`); return false;
      }
    }
    if (!/^\d{4}$/.test(book.year)) { setMessage('Year must be 4 digits.'); return false; }
    return true;
  };

  const addBook = async () => {
    if (!validateForm()) return;
    try {
      const { id, ...bookWithoutId } = book;
      const res = await axios.post(`${baseUrl}/add`, bookWithoutId);
      setMessage(res.data); fetchAllBooks(); resetForm();
    } catch (error) { setMessage(error.response?.data || 'Error adding book.'); }
  };

  const updateBook = async () => {
    if (!validateForm()) return;
    try { const res = await axios.put(`${baseUrl}/update`, book); setMessage(res.data); fetchAllBooks(); resetForm(); }
    catch { setMessage('Error updating book.'); }
  };

  const deleteBook = async (id) => { try { const res = await axios.delete(`${baseUrl}/delete/${id}`); setMessage(res.data); fetchAllBooks(); }
    catch { setMessage('Error deleting book.'); }
  };

  const getBookById = async () => {
    try { const res = await axios.get(`${baseUrl}/get/${idToFetch}`); setFetchedBook(res.data); setMessage(''); }
    catch { setFetchedBook(null); setMessage('Book not found.'); }
  };

  const handleEdit = (bk) => { setBook(bk); setEditMode(true); setMessage(`Editing book with ID ${bk.id}`); };
  const resetForm = () => { setBook({ id: '', title: '', author: '', genre: '', publisher: '', year: '' }); setEditMode(false); setFetchedBook(null); };

  return (
    <div className="book-container">
      {message && <div className={`message-banner ${message.toLowerCase().includes('error') ? 'error' : 'success'}`}>{message}</div>}

      <h2>Book Management</h2>

      <div>
        <h3>{editMode ? 'Edit Book' : 'Add Book'}</h3>
        <div className="form-grid">
          {editMode && <input type="number" name="id" value={book.id} disabled />}
          <input type="text" name="title" placeholder="Title" value={book.title} onChange={handleChange} />
          <input type="text" name="author" placeholder="Author" value={book.author} onChange={handleChange} />
          <input type="text" name="genre" placeholder="Genre" value={book.genre} onChange={handleChange} />
          <input type="text" name="publisher" placeholder="Publisher" value={book.publisher} onChange={handleChange} />
          <input type="number" name="year" placeholder="Year" value={book.year} onChange={handleChange} />
        </div>

        <div className="btn-group">
          {!editMode ? <button className="btn-blue" onClick={addBook}>Add Book</button> :
            <>
              <button className="btn-green" onClick={updateBook}>Update Book</button>
              <button className="btn-gray" onClick={resetForm}>Cancel</button>
            </>}
        </div>
      </div>

      <div>
        <h3>Get Book By ID</h3>
        <input type="number" value={idToFetch} onChange={(e) => setIdToFetch(e.target.value)} placeholder="Enter ID" />
        <button className="btn-blue" onClick={getBookById}>Fetch</button>
        {fetchedBook && <div><h4>Book Found:</h4><pre>{JSON.stringify(fetchedBook, null, 2)}</pre></div>}
      </div>

      <div>
        <h3>All Books</h3>
        {books.length === 0 ? <p>No books found.</p> :
          <div className="table-wrapper">
            <table>
              <thead>
                <tr><th>id</th><th>title</th><th>author</th><th>genre</th><th>publisher</th><th>year</th><th>Actions</th></tr>
              </thead>
              <tbody>
                {books.map((bk) => (
                  <tr key={bk.id}>
                    <td>{bk.id}</td><td>{bk.title}</td><td>{bk.author}</td><td>{bk.genre}</td><td>{bk.publisher}</td><td>{bk.year}</td>
                    <td>
                      <div className="action-buttons">
                        <button className="btn-green" onClick={() => handleEdit(bk)}>Edit</button>
                        <button className="btn-red" onClick={() => deleteBook(bk.id)}>Delete</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>}
      </div>
    </div>
  );
};

export default BookManager;
