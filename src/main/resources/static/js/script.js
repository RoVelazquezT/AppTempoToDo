// Editar Tarea
document.addEventListener('DOMContentLoaded', function() {
	let currentTaskId = null;

	const saveBtn = document.getElementById('save-changes');

	document.getElementById('editModal').addEventListener('show.bs.modal', function(event) {
		const button = event.relatedTarget; 
		currentTaskId = button.getAttribute('data-id');

		document.getElementById('edit-title').value = button.getAttribute('data-title');
		document.getElementById('edit-content').value = button.getAttribute('data-content');
	});

	// Guardar cambios
	saveBtn.addEventListener('click', async function() {
		const updatedTask = {
			title: document.getElementById('edit-title').value,
			description: document.getElementById('edit-content').value
		};

		try {
			const token = document.querySelector("meta[name='_csrf']").content;
			const header = document.querySelector("meta[name='_csrf_header']").content;

			const response = await fetch(`/tasks/${currentTaskId}`, {
				method: "PUT",
				headers: {
					"Content-Type": "application/json",
					[header]: token
				},
				body: JSON.stringify(updatedTask),
				credentials: "same-origin"
			});

			if (response.ok) {
				console.log("Tarea actualizada correctamente");
				location.reload(); 
			} else {
				console.error("Error al actualizar la tarea:", response.status);
			}
		} catch (err) {
			console.error("Error en la petición:", err);
		}

		const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
		modal.hide();
	});
});


// Complete de task
document.addEventListener("DOMContentLoaded", () => {
	const token = document.querySelector("meta[name='_csrf']").content;
	const header = document.querySelector("meta[name='_csrf_header']").content;

	const buttons = document.querySelectorAll(".btn-done");

	buttons.forEach(button => {
		button.addEventListener("click", async () => {
			const taskId = button.getAttribute("data-id");

			console.log("Se clickeó el botón con el id: ", taskId);

			try {
				const response = await fetch(`/tasks/${taskId}/complete`, {
					method: "POST",
					headers: {
						"Content-Type": "application/json",
						[header]: token   
					},
					credentials: "same-origin" 
				});

				if (response.ok) {
					console.log("response.ok - id: ", taskId);
					button.disabled = true;
					location.reload();
				} else {
					console.error("Error al completar la tarea", response.status, response.statusText);
				}
			} catch (err) {
				console.error("Error en la petición:", err);
			}
		});
	});
});
 
// Ver Tarea
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('detailModal').addEventListener('show.bs.modal', function(event) {
		const card = event.relatedTarget.closest('.card');

		const title = card.querySelector('.card-title').textContent;
		const content = card.querySelector('.card-text').textContent;
		const date = card.querySelector('.card-date').textContent;

		document.getElementById('detail-title').textContent = title;
		document.getElementById('detail-content').textContent = content;
		document.getElementById('detail-date').textContent = date;
	});
});


//Eliminar Tara
document.addEventListener("DOMContentLoaded", () => {
  const csrfToken = document.querySelector('meta[name="_csrf"]').content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

  document.querySelectorAll(".btn-delete").forEach(button => {
    button.addEventListener("click", async () => {
      const taskId = button.getAttribute("data-id");

      if (!confirm("¿Seguro que quieres eliminar esta tarea?")) return;

      try {
        const response = await fetch(`/tasks/${taskId}/delete`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
          }
        });

        if (response.ok) {
          button.closest(".card").remove();
        } else {
          alert("Error al eliminar la tarea");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("No se pudo conectar con el servidor");
      }
    });
  });
});
