function Loader({ text = "Loading..." }) {

    return (

        <div className="text-center my-4">

            <div className="spinner-border text-primary" role="status">
                <span className="visually-hidden">Loading...</span>
            </div>

            <p className="mt-2 text-muted">{text}</p>

        </div>

    );
}

export default Loader;